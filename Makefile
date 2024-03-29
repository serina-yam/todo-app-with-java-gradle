DOCKER_COMPOSE_BASH := cd .devcontainer/ && docker-compose exec todo-app-with-java-gradle bash -c
APP_DIR := home/vscode/workspace/app
current_dir := $(shell cd)

ifeq ($(OS),Windows_NT)
    OPEN_TEST_CMD = call open_test.bat "$(current_dir)"
    OPEN_COVERAGE_CMD = call open_coverage.bat "$(current_dir)"
    OPEN_JAVADOC_CMD = call open_javadoc.bat "$(current_dir)"
else
    OPEN_TEST_CMD = open ./app/build/reports/tests/test/index.html
    OPEN_COVERAGE_CMD = open ./app/build/reports/jacoco/test/html/index.html
    OPEN_JAVADOC_CMD = open ./app/build/docs/javadoc/index.html
endif

# ヘルプメッセージを表示
help:
	@echo "-----------------------------------------------------"
	@echo "使用可能なコマンド一覧:"
	@echo "-----------------------------------------------------"
	@echo "  setup             : Docker環境構築"
	@echo "  build             : gradleプロジェクトをビルド"
	@echo "  rebuild           : gradleプロジェクトを再度ビルド"
	@echo "  clean             : クリーンアップ"
	@echo "  run               : アプリケーションを実行"
	@echo "  test              : テストを実行"
	@echo "- 結果を見る ---------------------------------------"
	@echo "  open-test         : テスト結果のhtmlを開く"
	@echo "  open-coverage     : カバレッジのhtmlを開く"
	@echo "  open-javadoc      : javadocのhtmlを開く"
	@echo "- パス確認 -----------------------------------------"
	@echo "  show-test         : テスト結果htmlのパスを表示"
	@echo "  show-coverage     : カバレッジhtmlのパスを表示"
	@echo "  show-javadoc      : javadoc htmlのパスを表示"
	@echo "  show-jar          : 作成されたjarファイルを確認"

# Docker Composeを使用して、プロジェクトの実行環境をセットアップ
setup:
	cd .devcontainer/ && docker-compose up -d

# アプリケーションのビルド
build:
	$(DOCKER_COMPOSE_BASH) "cd $(APP_DIR) && gradle wrapper && ./gradlew build"

# 再度ビルド
rebuild:
	$(DOCKER_COMPOSE_BASH) "cd $(APP_DIR) && ./gradlew build"

# ビルド後のjarファイルの確認
show-jar:
	ls app/build/libs/

# クリーンアップ
clean:
	${DOCKER_COMPOSE_BASH} "cd $(APP_DIR) && ./gradlew clean"

# アプリケーションの実行
run:
	${DOCKER_COMPOSE_BASH} "cd $(APP_DIR) && java -jar build/libs/todo-app-with-java-gradle-0.0.1-SNAPSHOT.jar"

# テストを実行
# cd app/ && ./gradlew test
test:
	${DOCKER_COMPOSE_BASH} "cd $(APP_DIR) && ./gradlew test"

javadoc:
	${DOCKER_COMPOSE_BASH} "cd $(APP_DIR) && ./gradlew javadoc"

# テスト結果のhtmlを開く
open-test:
	$(OPEN_TEST_CMD)

# カバレッジをhtmlを開く
open-coverage:
	$(OPEN_COVERAGE_CMD)

open-javadoc:
	$(OPEN_JAVADOC_CMD)

# テスト結果htmlのパスを表示
show-test:
	@echo "./app/build/reports/tests/test/index.html"

# カバレッジhtmlのパスを表示
show-coverage:
	@echo "./app/build/reports/jacoco/test/html/index.html"

show-javadoc:
	@echo "./app/build/docs/javadoc/index.html"
