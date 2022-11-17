<#-- @ftlvariable name="users" type="ru.itmo.wp.model.domain.User[]" -->
<#-- @ftlvariable name="talks" type="ru.itmo.wp.model.dto.TalkDTO[]" -->
<#-- @ftlvariable name="text" type="java.lang.String" -->
<#-- @ftlvariable name="error" type="java.lang.String" -->
<#import "commons.ftlh" as c>

<@c.page>
    <div class="talk">
        <div class="form-box">
            <div class="header">Send message</div>
            <div class="body">
                <form method="post" action="">
                    <input type="hidden" name="action" value="send"/>
                    <div class="field">
                        <div class="name">
                            <label for="target_user_id">Send to</label>
                        </div>
                        <div class="value">
                            <select id="target_user_id" name="target_user_id">
                                <#list users as user>
                                    <option value="${user.id}">${user.login}</option>
                                </#list>
                            </select>
                        </div>
                    </div>
                    <div class="field">
                        <div class="name">
                            <label for="text">Text</label>
                        </div>
                        <div class="value">
                            <textarea id="text" name="text" type="text" value="${text!}"></textarea>
                        </div>
                    </div>
                    <#if error??>
                        <div class="error">
                            ${error!}
                        </div>
                    </#if>
                    <div class="button-field">
                        <input type="submit" value="Send">
                    </div>
                </form>
            </div>
        </div>
        <div class="datatable">
            <div class="caption">Talk</div>
            <table>
                <thead>
                <tr>
                    <th>Id</th>
                    <th>From user</th>
                    <th>to user</th>
                    <th>Text</th>
                    <th>Created</th>
                </tr>
                </thead>
                <tbody>
                <#list talks as talk>
                    <tr>
                        <td>${talk.id}</td>
                        <td>${talk.fromUser}</td>
                        <td>${talk.toUser}</td>
                        <td>${talk.text}</td>
                        <td>${talk.creationTime}</td>
                    </tr>
                </#list>
                </tbody>
            </table>
        </div>
    </div>
</@c.page>