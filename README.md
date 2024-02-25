## はじめに

このアプリの詳細はWikiの「[Home](https://github.com/serina-yam/todo-app-with-java-gradle/wiki)」にまとめています。

## 実行環境

- Docker 25.0.2
- Docker Compose v2.24.3-desktop.1

※<br />
Windowsの場合は、makeコマンドが使用できる状態[^1]で実施してください。<br />
インストールせずに実施する場合は、Makefileを参照して実施をお願いします。

[^1]: GnuWin32をインストールすることで使用可能になります

<!-- TODO Makefoleのパスをリンクする -->

## スタートガイド

### 💻ローカル環境構築方法
コマンドでの実行方法を記載します。<br />
VSCodeでの実行方法は、Wikiの「[スタートガイド ‐ VSCode使用](https://github.com/serina-yam/todo-app-with-java-gradle/wiki/%E3%82%B9%E3%82%BF%E3%83%BC%E3%83%88%E3%82%AC%E3%82%A4%E3%83%89-%E2%80%90-VSCode%E4%BD%BF%E7%94%A8)」を参照してください。
#### 1. リポジトリをローカル環境にクローン

```zsh
git clone https://github.com/serina-yam/todo-app-with-java-gradle.git
```
```zsh
cd todo-app-with-java-gradle
```


#### 2. Docker Composeを使用して、プロジェクトの実行環境をセットアップ

```zsh
make setup
```

#### 3. アプリケーションの実行

```zsh
make run
```

#### 4. ブラウザで確認

http://localhost:8080


### ⛏️ビルド方法

```zsh
make build
```


### ⛑️テスト実行方法

#### 1. テスト実行

```zsh
make test
```

#### 2. ビルド時に作成されたテスト結果を見る

```zsh
make open-test
```

#### 3. ビルド時に作成されたカバレッジ結果を見る

```zsh
make open-coverage
```
