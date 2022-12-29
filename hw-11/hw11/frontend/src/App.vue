<template>
    <div id="app">
        <Header :user="user"/>
        <Middle :user="user" :posts="posts" :users="users"/>
        <Footer/>
    </div>
</template>

<script>
import Header from "./components/Header";
import Middle from "./components/Middle";
import Footer from "./components/Footer";
import axios from "axios"

export default {
    name: 'App',
    components: {
        Footer,
        Middle,
        Header
    },
    data: function () {
        return {
            user: null,
            posts: [],
            users: []
        }
    },
    methods: {
        updateAll() {
            this.$root.$emit("onUpdatePosts")
            this.$root.$emit("onUpdateUsers")
        },
        stopTimer () {
            if (this.interval) {
                window.clearInterval(this.interval)
            }
        },
        startTimer () {
            this.stopTimer()
            this.interval = window.setInterval(() => {
                this.updateAll()
            }, 10000)
        }
    },
    mounted () {
        this.startTimer()
    },
    beforeDestroy () {
        this.stopTimer()
    },
    beforeMount() {
        this.updateAll()
    },
    beforeCreate() {
        this.$root.$on("onEnter", (login, password) => {
            if (password === "") {
                this.$root.$emit("onEnterValidationError", "Password is required");
                return;
            }

            axios.post("/api/1/jwt", {
                login, password
            }).then(response => {
                localStorage.setItem("jwt", response.data);
                this.$root.$emit("onJwt", response.data);
            }).catch(error => {
                this.$root.$emit("onEnterValidationError", error.response.data);
            });
        });

        this.$root.$on("onRegister", (login, name, password) => {
            if (login === "") {
                this.$root.$emit("onRegisterValidationError", "Login is required");
                return;
            }
            if (name === "") {
                this.$root.$emit("onRegisterValidationError", "Name is required");
                return;
            }
            if (password === "") {
                this.$root.$emit("onRegisterValidationError", "Password is required");
                return;
            }

            axios.post("/api/1/users/register", {
                login, name, password
            }).then(response => {
                localStorage.setItem("jwt", response.data);
                this.$root.$emit("onUpdateUsers");
                this.$root.$emit("onJwt", response.data);
            }).catch(error => {
                this.$root.$emit("onRegisterValidationError", error.response.data);
            });
        });

        this.$root.$on("onJwt", (jwt) => {
            localStorage.setItem("jwt", jwt);

            axios.get("/api/1/users/auth", {
                params: {
                    jwt
                }
            }).then(response => {
                this.user = response.data;
                this.$root.$emit("onChangePage", "Index");
            }).catch(() => this.$root.$emit("onLogout"))
        });

        this.$root.$on("onLogout", () => {
            localStorage.removeItem("jwt");
            this.user = null;
            this.$root.$emit("onChangePage", "Index");
        });

        this.$root.$on("onWritePost", (title, text) => {
            const user = this.user
            if (user === null) {
                this.$root.$emit("onWritePostValidationError", "You should login before write post");
                return;
            }
            if (title === "") {
                this.$root.$emit("onWritePostValidationError", "title is required");
                return;
            }
            if (text === "") {
                this.$root.$emit("onWritePostValidationError", "text is required");
                return;
            }

            const jwt = localStorage.getItem("jwt")
            axios.post("/api/1/posts/writePost", {
                title, text
            }, {
                params: {
                    jwt
                }
            }).then(() => {
                this.$root.$emit("onUpdatePosts");
            }).catch(error => {
                this.$root.$emit("onWritePostValidationError", error.response.data);
            });
        });

        this.$root.$on("onUpdateUsers", () => {
            axios.get("/api/1/users").then(response => {
                this.users = response.data;
            });
        });

        this.$root.$on("onUpdatePosts", () => {
            axios.get("/api/1/posts").then(response => {
                this.posts = response.data;
            });
        });
    },
}
</script>

<style>
#app {

}
</style>
