<?php
ob_start();
include './connect.php';
session_start();
?>
<html>
    <head>
        <title>Payment Sucessful</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="stylesheet" type="text/css" href="css/confirmation_pay.css">
    </head>
    <body>
        <main>
            <?php 
            include './header.php';
            ?>
            <section>
                <center><h1 class="message">Your payment reference has been sent to your email! <br> </h1>
                    <h1>Thanks for buying in Tickify! See you the next time!<br></h1>
                    <form action="index.php">
                    <button class="back">Back to Home Page!</button>
                    </form>
                </center>
                
            </section>
        <footer>
            <?php include './footer.php';?>
        </footer>
        </main>
    </body>
</html>
