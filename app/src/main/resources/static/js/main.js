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
