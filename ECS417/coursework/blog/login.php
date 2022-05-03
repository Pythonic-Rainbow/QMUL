<?php
    # Only attempts to login if both param are set
    if (isset($_GET['email']) and isset($_GET['password'])) {
        $con = new mysqli('localhost', 'root');  # Connection to database

        # Prevents SQL Injection
        $stmt = $con->prepare("SELECT 1 FROM blog.users WHERE email = ? AND password = ?");
        $stmt->bind_param('ss', $_GET['email'], $_GET['password']);
        $stmt->execute();

        # Login successful
        if ($stmt->get_result()->num_rows) {
            session_start();
            $_SESSION["login"] = true;
            header('Location: addPost.php');
        }
    }
?>

<link rel="stylesheet" href="../reset.css">
<link rel="stylesheet" href="actions.css">

<div id="login">
    <section>
        <div class="test" id="login-txt">
            <h1>Login.</h1>
        </div>
        <div id="login-form">
            <form action="login.php">
                <input type="email" name="email" id="email" placeholder="Email">
                <input type="password" name="password" id="password" placeholder="Password">
                <input type="submit" id="submit" value="Submit">
            </form>
        </div>
    </section>
</div>