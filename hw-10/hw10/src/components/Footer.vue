<template>
    <footer>
        <a href="/">Codeforces</a> &copy; 2022 by Mike Mirzayanov
        <div>{{ countUsers }} users, {{ countPosts }} posts</div>
    </footer>
</template>

<script>
export default {
    name: "Footer",
    props: ["countUsers", "countPosts"]
}
</script>

<style scoped>

</style>