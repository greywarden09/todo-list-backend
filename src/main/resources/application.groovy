datasources {
    'default' {
        url = "jdbc:postgresql://localhost/todo_list"
        username = "postgres"
        password = "postgres"
        autoCommit = true
    }
}

micronaut {
    application {
        name = "todo-list-backend"
    }
    server {
        cors {
            enabled = true
        }
    }
}
