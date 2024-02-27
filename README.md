## はじめに

このアプリの詳細は Wiki の「[Home](https://github.com/serina-yam/todo-app-with-java-gradle/wiki)」にまとめています。

## 実行環境

- Docker 25.0.2
- Docker Compose v2.24.3-desktop.1

## 動作確認環境

以下の環境で動作確認済みになります。

### mac

- チップ: Apple M1
- メモリ: 8GB
- macOS: macOS Sonoma v14.3

### windows

- プロセッサ:	Intel Core i7
- 実装 RAM:	16.0 GB
- エディション:	Windows 10 Pro
- バージョン:	22H2

※<br />
Windows の場合は、make コマンドが使用できる状態[^1]で実施してください。<br />
インストールせずに実施する場合は、[Makefile](https://github.com/serina-yam/todo-app-with-java-gradle/blob/main/Makefile) を参照して実施をお願いします。

[^1]: GnuWin32 をインストールすることで使用可能になります

## スタートガイド

### 💻 ローカル環境構築方法

コマンドでの実行方法を記載します。<br />
VSCode での実行方法は、Wiki の「[スタートガイド ‐ VSCode 使用](https://github.com/serina-yam/todo-app-with-java-gradle/wiki/%E3%82%B9%E3%82%BF%E3%83%BC%E3%83%88%E3%82%AC%E3%82%A4%E3%83%89-%E2%80%90-VSCode%E4%BD%BF%E7%94%A8)」を参照してください。

#### 1. リポジトリをローカル環境にクローン

```bash
git clone https://github.com/serina-yam/todo-app-with-java-gradle.git
```

```bash
cd todo-app-with-java-gradle
```

#### 2. application.properties の置き換え

以下にあるファイルを、AWS S3 で配布したファイルに置き換えてください。<br />
パス： app/src/main/resources/application.properties<br />

コマンドを使用する場合の例です。

```bash
mv -f /Users/XXXXXX/Desktop/application.properties app/src/main/resources/application.properties
```

#### 3. Docker Compose を使用して、プロジェクトの実行環境をセットアップ

```bash
make setup
```

#### 4. アプリケーションのビルド

```bash
make build
```

#### 5. アプリケーションの実行

```bash
make run
```

#### 6. ブラウザで確認

http://localhost:8080

#### 7. データベース管理ツールをブラウザで開く

http://localhost:9000

### ⛏️ ビルド方法

#### 1. ビルド

```bash
make build
```

#### 2. クリーンアップ

```bash
make clean
```

### ⛑️ テスト実行方法

#### 1. テスト実行

```bash
make test
```

#### 2. ビルド時に作成されたテスト結果を見る

```bash
make open-test
```

#### 3. ビルド時に作成されたカバレッジ結果を見る

```bash
make open-coverage
```

### 📖ドキュメント

#### 1. Javadoc生成

```bash
make javadoc
```


#### 2. 作成されたJavadocを見る
```bash
make open-javadoc
```
