const cardBody = document.querySelectorAll(".card-body");
const secondCardBodyButtons = cardBody[1].querySelectorAll(".showw");
const secondCardBodyCancel = cardBody[1].querySelectorAll(".cancele");
const secondCardBodyLi = cardBody[1].querySelectorAll("li");
const cardSButton1 = secondCardBodyButtons[0];
const cardSButton2 = secondCardBodyButtons[1];
const cardSButton3 = secondCardBodyButtons[2];

cardSButton1.addEventListener("click",function (event){
    cardSButton1.style.visibility = "hidden";
    secondCardBodyLi[1].querySelector("form").style.visibility="visible";
    secondCardBodyCancel[0].style.visibility="visible";
    secondCardBodyCancel[0].addEventListener("click",function (event){
        secondCardBodyLi[1].querySelector("form").style.visibility="hidden";
        secondCardBodyCancel[0].style.visibility="hidden";
        cardSButton1.style.visibility="visible";
    })

})

cardSButton2.addEventListener("click",function (event) {
    cardSButton2.style.visibility = "hidden";
    secondCardBodyLi[2].querySelector("form").style.visibility = "visible";
    secondCardBodyCancel[1].style.visibility = "visible";
    secondCardBodyCancel[1].addEventListener("click", function (event) {
        secondCardBodyLi[2].querySelector("form").style.visibility = "hidden";
        cardSButton2.style.visibility="visible";
        secondCardBodyCancel[1].style.visibility = "hidden";

    })
})

