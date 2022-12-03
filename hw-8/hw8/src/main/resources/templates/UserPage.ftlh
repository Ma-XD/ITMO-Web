<#import "commons.ftlh" as c>

<@c.page>
    <#if userDTO??>
        <div class="user">
            <div class="info">
                <ul>
                    <li>id: ${userDTO.id}</li>
                    <li>login: ${userDTO.login}</li>
                    <li>creationTime: ${userDTO.creationTime}</a></li>
                </ul>
            </div>
        </div>
    <#else>
        No such user
    </#if>
</@c.page>