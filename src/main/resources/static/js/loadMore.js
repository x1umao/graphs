function loadMore() {
    $('#loadMore').click(function () {
        if (pageNumber > totalPage) {
            return;
        }
        pageNumber++;
        console.log(pageNumber);
        let loadMoreBtn = $('#loadMore');
        $.get(`loadmore?category=${category}&page=${pageNumber}&keyword=${keyword}`, d => {
            // $(`#card${category}>input:first`);
            if (category === 0) {
                d.personListVOS.forEach(data => {
                    let card = $('#card0').clone();
                    card.find('h4#name').html(data.name);
                    card.find('p#gender').html(data.gender);
                    card.find('p#gender').html(data.status);
                    card.find('p#counter').html(data.counter);
                    card.find('a').attr('href', `person-detail?name=${data.name}`);
                    loadMoreBtn.before(card);
                });
            } else if (category === 1) {
                d.articleListVOS.forEach(data => {
                    let card = $('#card1').clone();
                    card.find('h4#title').html(data.title);
                    card.find('p#year').html(data.year);

                    if (data.journalTitle === null) {
                        card.find('p#journalTitle').html('N.A');
                    } else {
                        card.find('p#journalTitle').html(data.journalTitle);
                    }

                    card.find('p#firstAuthor').html(data.firstAuthor);
                    card.find('a').attr('href', `article-detail?title=${data.title}`);
                    loadMoreBtn.before(card);
                })
            } else if (category === 2) {
                d.journalListVOS.forEach(data => {
                    let card = $("#card2").clone();
                    card.find('h4#title').html(data.title);
                    card.find('p#counter').html(data.counter);
                    card.find('a').attr('href', `journal-detail?title=${data.title}`);
                    loadMoreBtn.before(card);
                })
            }
        })
    });
}