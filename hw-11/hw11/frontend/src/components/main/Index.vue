<template>
    <div>
        <Article :posts="posts"/>
    </div>
</template>

<script>
import Article from "@/components/article/Article.vue";

export default {
    name: "Index",
    components: {Article},
    props: ["posts"]
}
</script>

<style scoped>

</style>