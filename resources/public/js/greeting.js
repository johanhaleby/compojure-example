$(function () {

    function sendGreeting(name) {
        if (!name) {
            alert("You must greet a name");
            return;
        }

        var api = $.ajax("/api"),
                greeting = api.then(function (data) {
                    var greetTemplate = data["_links"]["greet"]["href"];
                    var variables = { name: name };
                    var greetHref = UriTemplate.parse(greetTemplate).expand(variables);
                    return $.ajax(greetHref);
                });

        greeting.done(function (data) {
            var greeting = data["greeting"];
            $('#greetings').prepend("<div class='alert alert-info'>" + greeting + "</div>");
        });
    }

    $("#greet-btn").click(function () {
        var name = $('#name');
        sendGreeting(name.val());
        name.val("").focus();
    });

    $("#name").keypress(function (e) {
        if (e.which == 13) {
            $('#greet-btn').click();
        }
    });
});