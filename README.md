## スタートガイド

### 手順1: コマンドを使用する場合(全てルートディレクトリで実行)
#### 1. リポジトリをローカル環境にクローン

```zsh
git clone https://github.com/serina-yam/todo-app-with-java-gradle.git
```

#### 2. Docker Composeを使用して、プロジェクトの実行環境をセットアップ

```zsh
make setup
```

#### 3. アプリケーションのビルド

```zsh
make build
```

#### 4. アプリケーションの実行

```zsh
make run
```

#### 5. ブラウザで確認

http://localhost:8080

#### 6. テスト実行

```zsh
make test
```

#### 7.ビルド時に作成されたテスト結果を見る

```zsh
make open-test-result
```


### 手順2: VSCodeを使用する場合

#### 1. リポジトリをローカル環境にクローン

```bash
git clone https://github.com/serina-yam/todo-app-with-java-gradle.git
```

#### 2. インストールした「todo-app-with-java-gradle」ディレクトリをVSCodeで開く

#### 3. プラグイン「[Dev Containers](https://marketplace.visualstudio.com/items?itemName=ms-vscode-remote.remote-containers)」をインストールする

![Dev Containers](https://github.com/serina-yam/todo-app-with-java-gradle/assets/64587946/daeb67a3-aa20-4ddd-ae93-452ef9217e93)

#### 4. コマンドパレットで「開発コンテナ: コンテナでリビルドして再度開く」を選択

##### コマンドパレットを開く方法
- WindowsおよびLinux: Ctrl + Shift + P
- macOS: Command + Shift + P
- 上部メニューの表示＞コマンドパレットを選択
![開発コンテナ_コンテナでリビルドして再度開く](https://github.com/serina-yam/todo-app-with-java-gradle/assets/64587946/9a9993c6-ea74-456b-9f31-ed81b1466d13)


#### 5. アプリケーションを起動

左メニュー：エクスプローラーのJAVA PROJECTSから、todo-app-with-java-gradleの起動ボタンをクリックして起動する。
![JAVA PROJECTSからアプリケーションを起動](https://github.com/serina-yam/todo-app-with-java-gradle/assets/64587946/c78d4bf9-5016-456f-8f66-08486b4a349b)
![JAVA PROJECTSからアプリケーションを起動_確認](https://github.com/serina-yam/todo-app-with-java-gradle/assets/64587946/a3597f1c-7b8b-426c-a15c-44565e2300ed)

#### 6. ブラウザで確認

http://localhost:8080

#### 7.テスト実行

左メニュー：テストから実行したい箇所で起動ボタンをクリックして起動する

![test実行](https://github.com/serina-yam/todo-app-with-java-gradle/assets/64587946/4104b948-d54d-49c1-a4d8-91998fa7960d)

#### 8.ビルド時に作成されたテスト結果を見る

リモート接続を解除した状態でターミナルを開き、ルートディレクトで以下のコマンドを実行する。

```zsh
make open-test-result
```



## ER図
![er](https://github.com/serina-yam/todo-app-with-java-gradle/assets/64587946/4ff7b7c5-57b7-4ec7-a3b0-ad79d8c0d80e)
