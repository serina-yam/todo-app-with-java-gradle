package com.example.todoappwithjavagradle.controller;

import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ResolvableType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.todoappwithjavagradle.entity.User;
import com.example.todoappwithjavagradle.repository.UserRepository;
import com.example.todoappwithjavagradle.service.UserService;
import com.example.todoappwithjavagradle.util.AttributeKey;
import com.example.todoappwithjavagradle.util.LoginType;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.servlet.http.HttpSession;

/**
 * ログイン関連のコントローラークラス
 */
@Controller
public class LoginController {

    private static String authorizationRequestBaseUri = "oauth2/authorization";
    Map<String, String> oauth2AuthenticationUrls = new HashMap<>();

    private static final Logger logger = LogManager.getLogger(LoginController.class);

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ClientRegistrationRepository clientRegistrationRepository;

    @Autowired
    private HttpSession httpSession;

    /**
     * ログイン成功時の処理
     * 
     * @param authentication     認証情報
     * @param redirectAttributes リダイレクトの属性
     * @return ホーム画面へのリダイレクト
     * @throws UsernameNotFoundException ユーザーが見つからない場合にスローされる例外
     */
    @SuppressWarnings("null")
    @GetMapping("/login/success")
    @Transactional
    public String loginSuccess(Authentication authentication, RedirectAttributes redirectAttributes)
            throws UsernameNotFoundException {

        // データなし
        if (!authentication.isAuthenticated()) {
            return "redirect:/";
        }

        // フォーム画面の場合
        Integer userId = null;
        String username = null;
        if (authentication.getPrincipal() instanceof String) {
            // userId取得
            username = authentication.getPrincipal().toString();
            User info = userRepository.findByUsername(username);
            if (info == null) {
                throw new UsernameNotFoundException("ユーザーが見つかりません: " + authentication.getPrincipal());
            }

            userId = info.getUserId();
        }

        // プロバイダー認証の場合
        if (authentication.getPrincipal() instanceof OAuth2User) {

            OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
            String oauth2UserId = null;
            String loginType = null;
            if (oAuth2User.getAttributes().containsKey("id")) {
                // GitHub
                username = oAuth2User.getAttribute("login");
                oauth2UserId = oAuth2User.getAttribute("id").toString();
                loginType = LoginType.GITHUB.toString();
            } else if (oAuth2User.getAttributes().containsKey("sub")) {
                // Google
                username = oAuth2User.getAttribute("name");
                oauth2UserId = oAuth2User.getAttribute("sub").toString();
                loginType = LoginType.GOOGLE.toString();
            }

            // データがなかったときのみテーブル登録処理
            User info = userRepository.findByOauth2UserId(oauth2UserId);
            if (info == null) {
                userId = userService.signupUserFromProvider(username, oauth2UserId, loginType);
            } else {
                userId = info.getUserId();
            }
        }

        httpSession.setAttribute(AttributeKey.USER_ID.getValue(), userId);
        httpSession.setAttribute(AttributeKey.USERNAME.getValue(), username);
        logger.info("Session USER_ID: {}", userId);
        logger.info("Attribute USERNAME: {}", username);

        return "redirect:/"; // ホーム画面へリダイレクト
    }

    /**
     * ログイン画面表示処理
     * 
     * @param model モデル
     * @return ログイン画面のテンプレート名
     * @throws Exception 例外
     */
    @SuppressWarnings({ "unchecked", "null" })
    @GetMapping("/login")
    public String getLoginPage(Model model) throws Exception {

        Iterable<ClientRegistration> clientRegistrations = null;
        ResolvableType type = ResolvableType.forInstance(clientRegistrationRepository).as(Iterable.class);

        if (type != ResolvableType.NONE && ClientRegistration.class.isAssignableFrom(type.resolveGenerics()[0])) {
            clientRegistrations = (Iterable<ClientRegistration>) clientRegistrationRepository;
        }

        if (clientRegistrations != null) {
            clientRegistrations.forEach(registration -> oauth2AuthenticationUrls.put(registration.getClientName(),
                    authorizationRequestBaseUri + "/" + registration.getRegistrationId()));
            model.addAttribute(AttributeKey.URLS.getValue(), oauth2AuthenticationUrls);
        }

        return "login";
    }
}
