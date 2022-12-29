<template>
    <div class="users datatable" v-if="users" >
        <div class="caption">User</div>
        <table>
            <thead>
            <tr>
                <th>Id</th>
                <th>Login</th>
                <th>Name</th>
            </tr>
            </thead>
            <tbody>
            <UserTr v-for="user in users" :user="user" :key="user.id"/>
            </tbody>
        </table>
    </div>
    <div v-else>
        No users
    </div>
</template>

<script>
import UserTr from "@/components/table/UserTr.vue";

export default {
    name: "Users",
    components: {UserTr},
    props: ["users"]
}
</script>

<style scoped>

</style>