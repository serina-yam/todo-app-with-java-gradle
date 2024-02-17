JAVA_HOME := /home/vscode/.sdkman/candidates/java/current
DOCKER_COMPOSE_BASH := cd .devcontainer/ && docker-compose exec todo-app-with-java-gradle bash -c
APP_DIR := home/vscode/workspace/app

# ヘルプメッセージを表示
help:
	@echo "Available commands:"
	@echo "  test              : テストを実行"
	@echo "  clean             : クリーンアップ"
	@echo "  setup             : Docker環境構築"
	@echo "  build             : gradleプロジェクトをビルド"
	@echo "  check-jar         : 作成されたjarファイルを確認"
	@echo "  run               : アプリケーションを実行"
	@echo "  open-test-result  : テスト結果のhtmlを開く"

# テストを実行
test:
	cd app/ && ./gradlew test

# クリーンアップ
clean:
	cd app/ && ./gradlew clean

# Docker Composeを使用して、プロジェクトの実行環境をセットアップ
setup:
	cd .devcontainer/ && docker-compose up -d

# アプリケーションのビルド
build:
	$(DOCKER_COMPOSE_BASH) "export JAVA_HOME=$(JAVA_HOME) && cd $(APP_DIR) && sh ./gradlew build"

# ビルド後のjarファイルの確認
check-jar:
	${DOCKER_COMPOSE_BASH} "cd $(APP_DIR) && ls build/libs/"

# アプリケーションの実行
run:
	${DOCKER_COMPOSE_BASH} "export JAVA_HOME=$(JAVA_HOME) && export PATH=\$${PATH}:$(JAVA_HOME)/bin && cd $(APP_DIR) && java -jar build/libs/todo-app-with-java-gradle-0.0.1-SNAPSHOT.jar"

# テスト結果のhtmlを開く
open-test-result:
	open ./app/build/reports/tests/test/index.html
