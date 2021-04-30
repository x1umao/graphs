let searchObj = document.querySelector("#search");
let suggest = document.querySelector("#suggest");

searchObj.onkeyup = function(){
    let text = searchObj.value.toLowerCase();
    //长度为0时不请求
    if(text.length<=1){
        suggest.style.display = 'none';
        return;
    }
    //获取节点信息
    $.get(`/search?keyword=${text}`,data=>{
        let counter = 0;
        for (const key in data) {
            const NumOfNodes = data[key];
            let button = document.querySelector(`#${key}`);
            counter += NumOfNodes;
            button.innerHTML = `<em>${NumOfNodes}</em>`;
            if(NumOfNodes===0){
                button.parentNode.classList.add('disabled');
            }else{
                button.parentNode.classList.remove('disabled');
            }
        }
        let buttons = document.querySelectorAll('.suggestButton');
        let alert0 = document.querySelector('#alert0');
        if(counter === 0){
            for (let i = 0; i < buttons.length; i++) {
                buttons[i].style.display = 'none';
            }
            alert0.style.display = 'block';
        }else {
            for (let i = 0; i < buttons.length; i++) {
                buttons[i].style.display = 'block';
            }
            alert0.style.display = 'none';
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