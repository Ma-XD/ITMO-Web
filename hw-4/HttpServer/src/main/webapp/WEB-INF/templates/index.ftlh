<#import "./common.ftlh" as c>

<@c.page>
    <ul>
        <#list posts as p>
            <li><@c.post p true/></li>
        </#list>
    </ul>
</@c.page>
