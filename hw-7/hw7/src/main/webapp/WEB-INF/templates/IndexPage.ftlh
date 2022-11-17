<#import "commons.ftlh" as c>

<@c.page>
    <div class="articles">
        <ul>
            <template>
                <li>
                    <div class="title"></div>
                    <div class="body"><p></p></div>
                    <div class="footer">
                        <div class="left">
                            <img src="img/voteup.png" title="Vote Up" alt="Vote Up"/>
                            <span class="positive-score">+173</span>
                            <img src="img/votedown.png" title="Vote Down" alt="Vote Down"/>
                        </div>
                        <div class="right">
                            <img src="img/date_16x16.png" title="Publish Time" alt="Publish Time"/>
                            2 days ago
                            <img src="img/comments_16x16.png" title="Comments" alt="Comments"/>
                            <a href="#">68</a>
                        </div>
                    </div>
                </li>
            </template>
            <div class="noData">No data</div>
        </ul>
    </div>

    <script>
        $(function () {
            const $error = $(this).find(".error");
            const $noData = $(".articles .noData");
            $noData.text("Loading...")

            ajax({action: "findAll"}, $error, function (response) {
                response["articles"].forEach(function (article) {
                    $noData.hide();
                    const $li = $($(".articles template").prop("content")).clone();
                    $li.find(".title").text(article["title"]);
                    $li.find(".body p").text(article["text"]);
                    $(".articles ul").append($li);
                });
            })
        })
    </script>
</@c.page>
