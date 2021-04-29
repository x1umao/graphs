let searchObj = document.querySelector("#search");
let suggest = document.querySelector("#suggest");

searchObj.onkeyup = function(){
    let text = searchObj.value;
    //长度为0时不请求
    if(text.length<=1){
        suggest.style.display = 'none';
        return;
    }
    //获取节点信息
    $.get(`/search?keyword=${text}`,data=>{
        console.log(data);
        for (const key in data) {
            document.querySelector(`#${key}`).innerHTML = `<em>${data[key]}<em>`;
        }
    });
    suggest.style.display = 'block';
}
function toList(obj){
    const table = {
        "Person" : 0,
        "Article" : 1,
        "Journal" : 2
    }
    const category = obj.children[1].innerHTML;
    const keyword = document.querySelector("#search").value;
    window.location.href = `/listing?category=${table[category]}&keyword=${keyword}`;
}