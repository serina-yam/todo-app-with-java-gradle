## スタートガイド

### 手順1: コマンドを使用する場合
#### 1. リポジトリをローカル環境にクローン

```bash
git clone https://github.com/serina-yam/todo-app-with-java-gradle.git
```

#### 2. Docker Composeを使用して、プロジェクトの実行環境をセットアップ

```bash
cd todo-app-with-java-gradle/.devcontainer
docker-compose up -d
```

#### 3. コンテナ内でBashシェルを起動

```bash
docker-compose exec todo-app-with-java-gradle bash
```

#### 4. アプリケーションのビルド

```bash
cd home/vscode/workspace/app/
sh gradlew build
```

#### 5. 作成されたjarファイルの確認

```bash
ls build/libs/
```

#### 6. アプリケーションの実行

```bash
java -jar build/libs/todo-app-with-java-gradle-0.0.1-SNAPSHOT.jar
```

#### 7. ブラウザで確認

http://localhost:8080


### 手順2: VSCodeを使用する場合

#### 1. リポジトリをローカル環境にクローン

```bash
git clone https://github.com/serina-yam/todo-app-with-java-gradle.git
```

#### 2. インストールした「todo-app-with-java-gradle」ディレクトリをVSCodeで開く

#### 3. プラグイン「Dev Containers」をインストールする

https://marketplace.visualstudio.com/items?itemName=ms-vscode-remote.remote-containers
![Dev Containers](https://github.com/serina-yam/todo-app-with-java-gradle/assets/64587946/daeb67a3-aa20-4ddd-ae93-452ef9217e93)

#### 4. コマンドパレットで「開発コンテナ: コンテナでリビルドして再度開く」を選択

##### コマンドパレットを開く方法
- WindowsおよびLinux: Ctrl + Shift + P
- macOS: Command + Shift + P
- 上部メニューの表示＞コマンドパレットを選択
![開発コンテナ_コンテナでリビルドして再度開く](https://github.com/serina-yam/todo-app-with-java-gradle/assets/64587946/9a9993c6-ea74-456b-9f31-ed81b1466d13)


#### 5. 左メニュー：エクスプローラーのJAVA PROJECTSからアプリケーションを起動

todo-app-with-java-gradleの起動ボタンをクリックして起動する。
![JAVA PROJECTSからアプリケーションを起動](https://github.com/serina-yam/todo-app-with-java-gradle/assets/64587946/c78d4bf9-5016-456f-8f66-08486b4a349b)
![JAVA PROJECTSからアプリケーションを起動_確認](https://github.com/serina-yam/todo-app-with-java-gradle/assets/64587946/a3597f1c-7b8b-426c-a15c-44565e2300ed)

#### 6. ブラウザで確認

http://localhost:8080
