<?php
    # This file is awfully similar to index.php with a few exceptions
    session_start();
    if (!isset($_SESSION["login"]))
        header('Location: login.php');
?> 

<link rel="stylesheet" href="../reset.css">
<link rel="stylesheet" href="index.css">

<aside>
    <a href="addPost.php">Back</a>
    <!-- Forwards data to addPost.php so the post will actually be added. -->
    <a href=<?php echo 'addPost.php?' . $_SERVER['QUERY_STRING']?>>Upload</a>
</aside>

<?php
    date_default_timezone_set('UTC');
    $con = new mysqli('localhost', 'root');
    $result = $con->query('SELECT * FROM blog.posts');
    
    $rows = $result->fetch_all();
    array_push($rows, array($_GET['title'], $_GET['body'], time()));  # Just insert the preview blog entry and follows the logic in index.php
    for ($i = 0; $i < count($rows); $i++) {
        for ($j = 0; $j < count($rows) - 1; $j++) {
            if ($rows[$j][2] < $rows[$j+1][2]) {
                $temp = $rows[$j];
                $rows[$j] = $rows[$j+1];
                $rows[$j+1] = $temp;
            }
        }
    }

    foreach($rows as $row) {
        $date = date('jS F Y, H:i:s \U\TC', $row[2]);
        printf('<article><p><span>%s</span> | %s</p>%s<hr></article>', $row[0], $date, $row[1]);
    }
?>

<footer><i>This is the bottom of this page.</i></footer>