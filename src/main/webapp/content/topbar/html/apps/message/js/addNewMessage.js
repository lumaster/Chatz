function onClickNewMessage()
{
    var srcImage = '../../../../global/portraits/1.jpg';
    var classIcon = 'md-facebook-box'; //icon-line
    var name = 'Plai Test';
    var time = getTimeDiff("2018-05-20 18:20:03.123456");
    var countMessage = 3;

    var isMessageBox = onCheckIsMessageBox(name, classIcon);
    if (isMessageBox != -1)// already
    {
        onUpdateMessageBox(isMessageBox);
        onInsertMessageBox(srcImage, classIcon, name, time, countMessage);
        return;
    }
    else
    {
        onInsertMessageBox(srcImage, classIcon, name, time, countMessage);
    }

}

function onInsertMessageBox(srcImage, classIcon, name, time, countMessage)
{
    var htmls =
        '<li class="messageBoxList list-group-item active">' +
        '    <div class="media">' +
        '        <div class="pr-20">' +
        '            <a class="avatar" href="javascript:void(0)">' +
        '                <img class="img-fluid" src="' + srcImage + '" alt="...">' +
        '                <span class="' + classIcon + '"></span>' +
        '            </a>' +
        '        </div>' +
        '        <div class="media-body">' +
        '            <h5 class="nameMessageBox mt-0">' + name + '</h5>' +
        '            <span class="media-time">' + time + '</span>' +
        '        </div>' +
        '        <div class="pl-20">' +
        '            <span class="countMessage badge badge-pill badge-danger">' + countMessage + '</span>' +
        '        </div>' +
        '    </div>' +
        '</li>';

    $(htmls).insertBefore('#conversation');
}

function onCheckIsMessageBox(name,classIcon)
{
    var nameMessageBox;
    var messageBoxList = document.getElementsByClassName("messageBoxList");

    for (var i = 0; i < messageBoxList.length; i++)
    {
        nameMessageBox = messageBoxList[i].getElementsByClassName("nameMessageBox")[0].innerText;

        if (nameMessageBox == name)
        {
            if (messageBoxList[i].getElementsByClassName("md-facebook-box").length == 1)// is facebook already
            {
                return i;
            }
            else if (messageBoxList[i].getElementsByClassName("icon-line").length == 1)// is line already
            {
                return i;
            }
        }
    }
    return -1;
}

function onUpdateMessageBox(isMessageBox)
{
    var messageBoxList = document.getElementsByClassName("messageBoxList");
    messageBoxList[isMessageBox].remove();
}

function getTimeDiff(datetime) {
    var oneWeek = 1000 * 60 * 60 * 24 * 7;
    var datetime = typeof datetime !== 'undefined' ? datetime : new Date();

    var datetime = new Date(datetime).getTime();
    var now = new Date().getTime();

    if (isNaN(datetime)) {
        return "";
    }

    if (datetime < now) {
        var milisec_diff = now - datetime;
    } else {
        var milisec_diff = datetime - now;
    }

    var days = Math.floor(milisec_diff / 1000 / 60 / (60 * 24));

    var date_diff = new Date(milisec_diff);
    date_diff.setHours(date_diff.getHours() - 7);

    if (date_diff.getMonth()>0)
    {
        return date_diff.getMonth() + " month ago";
    }
    else if ((Math.floor(date_diff / oneWeek)) > 0)
    {
        return Math.floor(date_diff / oneWeek) + " week ago";
    }
    else if ((days % 30) > 0)
    {
        return days % 30 + " days ago";
    }
    else if (date_diff.getHours() > 0)
    {
        return date_diff.getHours() + " hours ago";
    }
    else if (date_diff.getMinutes() > 0)
    {
        return date_diff.getMinutes() + " minutes ago";
    }
    else if (date_diff.getSeconds() > 0)
    {
        return date_diff.getSeconds() + " sec ago";
    }
}



