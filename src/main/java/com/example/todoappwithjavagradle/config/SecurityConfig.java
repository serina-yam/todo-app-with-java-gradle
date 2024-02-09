package com.example.todoappwithjavagradle.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;


@Configuration
@EnableWebSecurity
public class SecurityConfig {

        private final CustomAuthenticationProvider customAuthenticationProvider;

        public SecurityConfig(CustomAuthenticationProvider customAuthenticationProvider){
            this.customAuthenticationProvider = customAuthenticationProvider;
        }

        @Bean
        public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
                http
                // カスタム認証プロバイダを設定
                .authenticationProvider(customAuthenticationProvider)
                // CORSの設定を適用
                .cors(customizer -> customizer.configurationSource(corsConfigurationSource()))
                // CSRFの保護を無効にする
                .csrf(csrf -> csrf.disable())
                .authorizeRequests(authorizeRequests ->
                        authorizeRequests
                                .antMatchers("/login", "/register").permitAll() // ログインページ、新規登録ページ、OAuth2エンドポイントは認証なしでアクセス可能
                                .anyRequest().authenticated()
                )
                .formLogin(formLogin ->
                        formLogin
                                .loginPage("/login") // ログインページのURL
                                .defaultSuccessUrl("/")
                                .permitAll()
                )
                .oauth2Login(oauth2Login ->
                        oauth2Login
                                .loginPage("/login") // ログインページのURL
                                .defaultSuccessUrl("/") // ログイン成功後のリダイレクト先
                )
                .formLogin(formLogin ->
                        formLogin
                                .loginPage("/login") // ログインページのURL
                                .permitAll()
                )
                .logout(logout ->
                        logout
                                .logoutUrl("/logout")
                                .logoutSuccessUrl("/login")
                )
                .csrf(csrf -> csrf.disable()); // CSRF保護を無効化

                return http.build();
        }

        /**
         * CORSの設定
         *
         * @return
         */
        @Bean
        public UrlBasedCorsConfigurationSource corsConfigurationSource() {
                // CORSの設定を行うためのオブジェクトを生成
                CorsConfiguration configuration = new CorsConfiguration();

                // クレデンシャル（資格情報（CookieやHTTP認証情報））を含むリクエストを許可する
                configuration.setAllowCredentials(true);

                // 許可するオリジン（この場合は"http://localhost:8080/"のみ）を設定
                configuration.addAllowedOrigin("http://localhost:8080/");

                // 任意のヘッダーを許可
                configuration.addAllowedHeader("*");

                // 任意のHTTPメソッド（GET, POSTなど）を許可
                configuration.addAllowedMethod("*");

                // CORS設定をURLベースで行うためのオブジェクトを生成
                UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();

                // 全てのURLパスにこのCORS設定を適用
                source.registerCorsConfiguration("/**", configuration);

                return source;
        }
}
