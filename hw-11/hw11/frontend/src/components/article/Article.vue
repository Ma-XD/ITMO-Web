<template>
    <div>
        <ArticlePost v-for="post in posts" :post="post" :key="post.id"/>
    </div>
</template>

<script>
import ArticlePost from "@/components/article/ArticlePost.vue";

export default {
    name: "Article",
    components: {ArticlePost},
    props: ["posts"]
}
</script>

<style scoped>

</style>