<?php
    session_start();

    # Check if logged in
    if (!isset($_SESSION["login"]))
        header('Location: login.php');

    if (!empty($_GET['title']) and !empty($_GET['body'])) {
        # Insert blog into database while preventing SQL Injection
        $con = new mysqli('localhost', 'root');
        $stmt = $con->prepare("INSERT INTO blog.posts VALUES (?, ?, ?)");
        $stmt->bind_param('ssi', $_GET['title'], $_GET['body'], time());
        $stmt->execute();
        header('Location: index.php');
    }
?>

<link rel="stylesheet" href="../reset.css">
<link rel="stylesheet" href="actions.css">

<div id="add">
    <form id="form" action="addPost.php">
        <input type="text" id="title" name="title" placeholder="Title">
        <input type="text" id="body" name="body" placeholder="Blog post body">
        <section>
            <p>New post</p>
            <input type="submit" onclick="previewww(event)" id="submit" value="Preview">
            <input type="submit" onclick="submittt(event)" id="submit" value="Submit">
            <input type="reset" onclick="return confirm_reset();" id="submit" value="Clear">
        </section>
    </form>
</div>

<script src="addPost.js"></script>