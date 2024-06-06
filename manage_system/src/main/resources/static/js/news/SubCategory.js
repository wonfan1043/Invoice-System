$(function() {
    // 初期のセクションを非表示にする
    const editItemBox = $("#edit-item-box");
    const addItemBox = $("#add-item-box");
    editItemBox.hide();
    addItemBox.hide();

    // ボタンのクリックを処理する
    $(".cancel").click(function() {
        editItemBox.fadeOut(100); // 編集ボックスを100ミリ秒かけてフェードアウトする
        addItemBox.fadeOut(100); // 追加ボックスを100ミリ秒かけてフェードアウトする
    });

    $(".edit-item").click(function() {
        const subCategoryId = $(this).attr("id");
        $("#subCategoryId").val(subCategoryId); // subCategoryId を hidden フィールドに設定する
        editItemBox.fadeIn(100); // 編集ボックスを100ミリ秒かけてフェードインする
    });

    $("#add-item").click(function() {
        addItemBox.fadeIn(100); // 追加ボックスを100ミリ秒かけてフェードインする
    });
});