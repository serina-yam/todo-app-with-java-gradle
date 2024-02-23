# Dangerfile

# Dangerを使ってTODOコメントを検出し、警告を出す
todo_comments = git.modified_files.map do |file_path|
  # 対象ファイルの内容を読み込み
  file_contents = File.read(file_path)
  
  # ファイル内のTODOコメントを検出し、行番号とコメント内容の配列を生成
  todo_comments_in_file = file_contents.each_line.with_index(1).select { |line, _| line.include?('TODO') }.map { |line, line_number| { file_path: file_path, line_number: line_number, comment: line.chomp } }
  
  # ファイル内のTODOコメントを返す
  todo_comments_in_file
end.flatten

# 警告を出す
if todo_comments.any?
  warn("プロジェクト内にTODOコメントが存在します:")
  todo_comments.each do |comment|
    warn("- #{comment[:file_path]} の #{comment[:line_number]} 行目: #{comment[:comment]}")
  end
end
