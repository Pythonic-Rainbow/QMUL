<link rel="stylesheet" href="../reset.css">
<link rel="stylesheet" href="index.css">

<header>
    <form action="index.php">
        Only show posts in: 
        <input type="text" name="year" placeholder="year">
        <select name="month">
            <option>1</option>
            <option>2</option>
            <option>3</option>
            <option>4</option>
            <option>5</option>
            <option>6</option>
            <option>7</option>
            <option>8</option>
            <option>9</option>
            <option>10</option>
            <option>11</option>
            <option>12</option>
        </select>
        <input type="submit" value="Filter">
        <input type="reset" value="Reset">
    </form>
    <hr>
</header>


<aside>
    <a href="addPost.php">Add Post</a>
    <?php
        session_start();
        # Only display if logged in
        if (isset($_SESSION['login'])) {
            echo 'Welcome!';
            echo '<a href="logout.php">Log out</a>';
        }
    ?>  
</aside>

<?php
    date_default_timezone_set('UTC');
    $query = 'SELECT * FROM blog.posts';

    # Limit blog entires to specific month?
    if (isset($_GET['year']) and $_GET['year'] != '' and isset($_GET['month'])) {
        $current = strtotime(sprintf('%u-%u-1', $_GET['year'], $_GET['month']));
        $limit = strtotime(sprintf('%u-%u-1 +1month', $_GET['year'], $_GET['month']));
        $query .= sprintf(' WHERE Time >= %u AND Time <= %u', $current, $limit);
    }

    $con = new mysqli('localhost', 'root');
    $result = $con->query($query);
    
    # Bubble sort all blog entries by time
    $rows = $result->fetch_all();
    for ($i = 0; $i < count($rows); $i++) {
        for ($j = 0; $j < count($rows) - 1; $j++) {
            if ($rows[$j][2] < $rows[$j+1][2]) {
                $temp = $rows[$j];
                $rows[$j] = $rows[$j+1];
                $rows[$j+1] = $temp;
            }
        }
    }

    # Display blog entries
    foreach($rows as $row) {
        $date = date('jS F Y, H:i:s \U\TC', $row[2]);
        printf('<article><p><span>%s</span> | %s</p>%s<hr></article>', $row[0], $date, $row[1]);
    }
?>

<footer><i>This is the bottom of this page.</i></footer>