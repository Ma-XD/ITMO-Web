<#-- @ftlvariable name="handle" type="java.lang.String" -->
<#-- @ftlvariable name="user_id" type="java.lang.Long" -->
<#import "./common.ftlh" as c>

<@c.page>
    <#if handle?has_content>
        <#assign u = c.findBy(users, "handle", handle)!/>
    <#elseif user_id?has_content>
        <#assign u = c.findBy(users, "id", user_id)!/>
    </#if>

    <#if u?has_content>
        <div class="user">
            <div class="info">
                <header>
                    <a href="#">${u.name}</a>
                </header>
                <ul>
                    <li>id: ${u.id}</li>
                    <li>handle: ${u.handle}</li>
                    <li>name: ${u.name}</li>
                    <li>posts: <a href="/posts?user_id=${u.id}">${c.countBy(posts, "userId", u.id)}</a></li>
                </ul>
            </div>
            <div class="logo">
                <img alt="User logo" title="User logo" src="/img/default-user-logo.png">
            </div>
        </div>

        <#assign next_u = c.findNext(users, "handle", u.handle)!/>
        <#assign prev_u = c.findPrev(users, "handle", u.handle)!/>
        <#assign forward_text = "→"/>
        <#assign back_text = "←"/>


        <nav>
            <div class="previous">
                <#if prev_u?has_content>
                    <@c.userlink prev_u.handle back_text/>
                <#else>
                    ${back_text}
                </#if>
            </div>
            <div class="next">
                <#if next_u?has_content>
                    <@c.userlink next_u.handle forward_text/>
                <#else>
                    ${forward_text}
                </#if>
            </div>
        </nav>
    <#else>
        No such user
    </#if>
</@c.page>
