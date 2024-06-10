database {
  username = "postgres"
  password = "postgres"
  url = "jdbc:postgresql://localhost/todo_list"
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
