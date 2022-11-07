<#import "./common.ftlh" as c>

<@c.page>
    <div class="datatable">
        <div class="caption">Submissions</div>
        <table>
            <thead>
            <tr>
                <th>User</th>
            </tr>
            </thead>
            <tbody>
            <#list users as u>
                <tr>
                    <td>
                        <#if user?? && user.id == u.id>*</#if>
                        <@c.userlink u.handle u.name/>
                    </td>
                </tr>
            </#list>
        </table>
    </div>
</@c.page>