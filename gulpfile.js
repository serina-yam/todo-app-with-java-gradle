/*
src 参照元を指定
dest 出力さきを指定
watch ファイル監視
series(直列処理)とparallel(並列処理)
*/
const { gulp, src, dest, watch, series, parallel } = require('gulp');

// プラグインを呼び出し
var sass = require('gulp-sass')(require('sass'));

// プラグインの処理をまとめる
const cssSass = () => {
  return src('./src/main/resources/static/sass/*.scss') //コンパイル元
    .pipe(sass({ outputStyle: 'expanded' }))
    .pipe(dest('./src/main/resources/static/css'))     //コンパイル先
}

// タスクをまとめて実行
exports.default = series(cssSass);


exports.watch = function() {
    watch('./src/main/resources/static/sass/*.scss', cssSass);
}

