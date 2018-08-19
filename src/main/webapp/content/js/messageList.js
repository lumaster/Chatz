
//============= start On Click Active List Message Box

$('.messageBoxList').click(function () {
    var messageBoxList = document.getElementsByClassName("messageBoxList");
    debugger;
    for (var i = 0; i < messageBoxList.length; i++)
    {
        messageBoxList[i].classList.remove("active");
    }
    this.classList.add("mystyle");
});

function onClickActiveListMessageBox()
{
    var aa = document.getElementById("conversation");
    console.log(aa);
}

//============= end On Click Active List Message Box
