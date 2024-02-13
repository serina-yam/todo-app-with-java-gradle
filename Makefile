JAVA_HOME := /home/vscode/.sdkman/candidates/java/current

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
	cd .devcontainer/ && \
	docker-compose exec todo-app-with-java-gradle bash -c "export JAVA_HOME=$(JAVA_HOME) && cd home/vscode/workspace/app/ && sh gradlew build"

# ビルド後のjarファイルの確認
check-jar:
	cd .devcontainer/ && \
	docker-compose exec todo-app-with-java-gradle bash -c "cd home/vscode/workspace/app/ && ls build/libs/"

# アプリケーションの実行
run:
	cd .devcontainer/ && \
	docker-compose exec todo-app-with-java-gradle bash -c "export JAVA_HOME=$(JAVA_HOME) && export PATH=\$${PATH}:$(JAVA_HOME)/bin && cd home/vscode/workspace/app/ && java -jar build/libs/todo-app-with-java-gradle-0.0.1-SNAPSHOT.jar"