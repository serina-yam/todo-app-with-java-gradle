"use strict";

/**
 * プルダウン背景 change event
 */
function changeBackgroundColor(select) {
  select.classList.add("changed");
}

/**
 * 選択された状態を hidden input にセット
 */
function setSelectedState(event) {
  var trElement = event.closest("tr");
  var stateSelect = trElement.querySelector("select");
  var stateInput = trElement.querySelector("#stateInput");
  stateInput.value = stateSelect.value;
}

/**
 * 削除前の確認ダイアログ
 * @param {*} form
 */
function confirmDelete(form) {
  if (!confirm("削除します。よろしいですか？")) {
    return false;
  }
}
