DOCKER_COMPOSE_BASH := cd .devcontainer/ && docker-compose exec todo-app-with-java-gradle bash -c
APP_DIR := home/vscode/workspace/app


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
	@echo "- パス確認 -----------------------------------------"
	@echo "  show-test         : テスト結果htmlのパスを表示"
	@echo "  show-coverage     : カバレッジhtmlをのパスを表示"
	@echo "  check-jar         : 作成されたjarファイルを確認"

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
check-jar:
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

# Windows用のコマンド
ifeq ($(OS),Windows_NT)
    open_CMD = start
else
    # その他のプラットフォーム用のコマンド
    open_CMD = open
endif

# テスト結果のhtmlを開く
open-test:
	$(open_CMD) ./app/build/reports/tests/test/index.html

# カバレッジをhtmlを開く
open-coverage:
	$(open_CMD) ./app/build/reports/jacoco/test/html/index.html

# テスト結果htmlのパスを表示
show-test:
	@echo "./app/build/reports/tests/test/index.html"

# カバレッジhtmlのパスを表示
show-coverage:
	@echo "./app/build/reports/jacoco/test/html/index.html"
