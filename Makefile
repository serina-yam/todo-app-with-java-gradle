# ヘルプメッセージを表示
help:
	@echo "使用可能なコマンド一覧:"
	@echo "  setup             : Docker環境構築"
	@echo "  build             : gradleプロジェクトをビルド"
	@echo "  check-jar         : 作成されたjarファイルを確認"
	@echo "  clean             : クリーンアップ"
	@echo "  run               : アプリケーションを実行"
	@echo "  test              : テストを実行"
	@echo "  open-test         : テスト結果のhtmlを開く"
	@echo "  open-coverage     : カバレッジのhtmlを開く"
	@echo "  show-test         : テスト結果htmlのパスを表示"
	@echo "  show-coverage     : カバレッジhtmlをのパスを表示"

# Docker Composeを使用して、プロジェクトの実行環境をセットアップ
setup:
	cd .devcontainer/ && docker-compose up -d

# アプリケーションのビルド
build:
	cd app/ && gradle wrapper && ./gradlew build

# ビルド後のjarファイルの確認
check-jar:
	ls app/build/libs/

# クリーンアップ
clean:
	cd app/ && ./gradlew clean

# アプリケーションの実行
run:
	java -jar app/build/libs/todo-app-with-java-gradle-0.0.1-SNAPSHOT.jar

# テストを実行
test:
	cd app/ && ./gradlew test

# テスト結果のhtmlを開く
open-test:
	open ./app/build/reports/tests/test/index.html

# カバレッジをhtmlを開く
open-coverage:
	open ./app/build/build/reports/jacoco/test/html/index.html

# テスト結果htmlのパスを表示
show-test:
	@echo "./app/build/reports/tests/test/index.html"

# カバレッジhtmlのパスを表示
show-coverage:
	@echo "./app/build/reports/jacoco/test/html/index.html"
