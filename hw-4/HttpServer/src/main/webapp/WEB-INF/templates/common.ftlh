<#macro page>
    <!DOCTYPE html>
    <html lang="ru">
    <head>
        <meta charset="UTF-8">
        <title>Codeforces</title>
        <link rel="stylesheet" href="/css/normalize.css">
        <link rel="stylesheet" href="/css/style.css">
        <link rel="icon" href="/favicon.ico">
    </head>
    <body>
    <@header/>
    <div class="middle">
        <@sidebar/>
        <main>
            <#nested/>
        </main>
    </div>
    <@footer/>
    </body>
    </html>
</#macro>

<#macro header>
    <header>
        <a href="#" class="logo"><img alt="Codeforces" title="Codeforces"
                                      src="/img/codeforces-logo-with-telegram.png"></a>
        <div class="languages">
            <a href="#"><img alt="In English" title="In English"
                             src="/img/gb.png"></a>
            <a href="#"><img alt="In Russian" title="In Russian"
                             src="/img/ru.png"></a>
        </div>
        <div class="enter-or-register-box">
            <#if user??>
                <@userlink user.handle user.name/>
                |
                <a href="/logout">Logout</a>
            <#else>
                <a href="/enter">Enter</a>
                |
                <a href="/register">Register</a>
            </#if>
        </div>
        <nav>
            <#assign items=[
            {"href": "/index", "name": "Home"},
            {"href": "/misc/help", "name": "Help"},
            {"href": "/contests", "name": "Contests"},
            {"href": "/users", "name": "Users"}
            ]/>
            <ul>
                <#list items as item>
                    <li>
                        <#if item.href == current_menu_page>
                            <a class="current" href="${item.href}">${item.name}</a>
                        <#else>
                            <a href="${item.href}">${item.name}</a>
                        </#if>
                    </li>
                </#list>
            </ul>
        </nav>
    </header>
</#macro>

<#macro sidebar>
    <aside>
        <ul>
            <#list posts as p>
                <li>
                    <section>
                        <div class="header">Post #${p.id}</div>
                        <div class="body">${reduceText(p.text)}</div>
                        <div class="footer">
                            <@postlink p.id "View all"/>
                        </div>
                    </section>
                </li>
            </#list>
        </ul>
    </aside>
</#macro>


<#macro post p is_reduced>
    <article>
        <div class="title">${p.title}</div>
        <#assign u = findBy(users, "id", p.userId)!/>
        <div class="information">By ${u.name}, n days ago</div>
        <div class="body">
            <p>
                <#if is_reduced>
                    ${reduceText(p.text)}
                <#else>
                    ${p.text}
                </#if>
            </p>
        </div>
        <ul class="attachment">
            <li>Announcement was written by <@userlink u.handle u.name/></li>
        </ul>
        <div class="footer">
            <div class="left">
                <img src="img/voteup.png" title="Vote Up" alt="Vote Up"/>
                <span class="positive-score">+173</span>
                <img src="img/votedown.png" title="Vote Down" alt="Vote Down"/>
            </div>
            <div class="right">
                <img src="img/date_16x16.png" title="Publish Time" alt="Publish Time"/>
                n days ago
                <img src="img/comments_16x16.png" title="Comments" alt="Comments"/>
                <a href="#">68</a>
            </div>
        </div>
    </article>
</#macro>

<#macro footer>
    <footer>
        2010-2021 &copy; by Mike Mirzayanov
    </footer>
</#macro>

<#macro userlink handle text>
    <a href="/user?handle=${handle}">${text}</a>
</#macro>

<#macro postlink post_id text>
    <a href="/post?post_id=${post_id}">${text}</a>
</#macro>

<#function countBy items key value>
    <#return items?filter(item -> item[key] == value)?size>
</#function>

<#function findBy items key value>
    <#return findAndShift(items, key,  value, 0)/>
</#function>

<#function findNext items key value>
    <#return findAndShift(items, key,  value, 1)/>
</#function>

<#function findPrev items key value>
    <#return findAndShift(items, key,  value, -1)/>
</#function>

<#function findAndShift items key value shift>
    <#list items as item>
        <#if item[key] == value>
            <#if (shift > 0 && item_index + shift < items?size)>
                <#return items[item_index + shift]/>
            <#elseif (shift < 0 && item_index + shift >= 0)>
                <#return items[item_index + shift]/>
            <#elseif (shift == 0)>
                <#return item/>
            </#if>
        </#if>
    </#list>
</#function>

<#function reduceText text>
    <#if (text?length > 350)>
        <#return text?substring(0, 351) + "..."/>
    <#else>
        <#return text/>
    </#if>
</#function>