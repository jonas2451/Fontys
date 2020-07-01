<?php
ob_start();
include './connect.php';
session_start();
    
//    $conn= new PDO('pgsql: host=localhost; dbname= tickify', $user, $pword);
//    $conn->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);
//    $conn->setAttribute(PDO::ATTR_EMULATE_PREPARES, false);
    
//    try{
//    $ticket_pay = $conn->prepare("select * from ticket where id_category = :category");
//    $ticket_pay->execute(array(':category'=>'0'));
//    $rows_ticketpay = $ticket_pay->fetchAll(PDO::FETCH_ASSOC);
        
//    }catch (PDOException $ex){
//        echo 'Error<br>';
//        echo $ex->getMessage();
//        exit;
//    }
    $shoppingcart_query =$conn->prepare("select * from shoppingcart where username = :username");
    $shoppingcart_query->bindParam(':username', $_SESSION['username'], PDO::PARAM_STR);
    $shoppingcart_query->execute();
    $rows_shoppingcart = $shoppingcart_query->fetch();
    
    $shoppcart_item = $conn->prepare("select * from shoppingcart_item where cart_id = :cart");
    $shoppcart_item ->bindParam(':cart', $rows_shoppingcart['cart_id'], PDO::PARAM_STR);
    $shoppcart_item->execute();
    $rows_shpcartitem= $shoppcart_item->fetchAll();
    
//    $ticketpay = $conn->prepare("select * from ticket where ticket_code = :nwcode");
//    $ticketpay->bindParam(':nwcode', $rows_shpcartitem['ticket_code'], PDO::PARAM_STR);
//    $ticketpay->execute();
//    $rows_ticketpay= $ticketpay->fetchAll();
    
?>
<!DOCTYPE html>
<html>
    <head>
        <title>PAYMENT</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="stylesheet" type="text/css" href="css/payment_page.css">
    </head>
    <body>
        <main>
            <?php
            include 'header.php';
           ?>
            <article>
                
                <img class="background" src="img/3.jpg"/>

                <section>
                    <center><h1 style="font-size: 30px">Tickets</h1></center>

                    <div class="grid-container">
                        <div class="grid-item">
                            Event: 
                        </div>
                        <div class="grid-item">
                            Price: 
                        </div>
                        <div class="grid-item">
                            No. Tickets: 
                        </div>
                        
                    </div>
                        <?php     
                            foreach ($rows_shpcartitem as $row ):
                            $ev_ct_name = $conn->prepare("select distinct(event_name), price, count(event_name)"
                            . "                   from event_ticket_category inner join ticket on etc_id=id_category"
                                    . "           where ticket_code = :code group by event_name, price");
                            $ev_ct_name->bindValue(':code',$row['ticket_code'], PDO::PARAM_STR);
                            $ev_ct_name->execute();
                            $rows_event= $ev_ct_name->fetch();  
                            
                        ?>
                        <div class="grid-container">
                            <div class="grid-item">
                                <?php echo $rows_event['event_name'];?> 
                            </div>
                            <div class="grid-item">
                                <?php echo $rows_event['price'];?>
                            </div>
                            <div class="grid-item">
                                <?php echo $rows_event['count'];?>
                            </div> 
                        </div>
                    <?php endforeach;?>
                </section>
                <aside>
                    <?php 
                    $userdata =$conn->prepare("select * from users where username = :nwusername");
                    $userdata->bindParam(':nwusername', $_SESSION['username'], PDO::PARAM_STR);
                    $userdata->execute();
                    $rows_usedata = $userdata->fetch();
                    ?>
                    <center><h3>Please enter your payment data:</h3></center>
                    <p>Name:
                    <input type="text" id="Searchbar2" value="<?php echo $rows_usedata['fname'];?>">
                    <a href="FILL IN LINK"></a></p>
                    <p>Surname:
                    <input type="text" id="Searchbar2" value="<?php echo $rows_usedata['lname'];?>">
                    <a href="FILL IN LINK"></a></p>
                    <p>Adress:
                    <input type="text" id="Searchbar2" value="<?php echo $rows_usedata['street'];?>">
                    <a href="FILL IN LINK"></a></p>
                    <p>Postal Code:
                    <input type="text" id="Searchbar2" value="<?php echo $rows_usedata['postal_code'];?>">
                    <a href="FILL IN LINK"></a></p>
                    <p>City:
                    <input type="text" id="Searchbar2" value="<?php echo $rows_usedata['city'];?>">
                    <a href="FILL IN LINK"></a></p>
                    <p>Country:
                    <input type="text" id="Searchbar2" value="<?php echo $rows_usedata['country'];?>">
                    <a href="FILL IN LINK"></a></p>
                    <center><h3>If these details are correct please click on "send" to receive an email about how to pay, thank you <3</h3></center>
                    
                    
                    <form action="confirmation_payment.php">
                    <center><button class="send">SEND!</button></center>
                    </form>
                </aside>
            </article>
            
            <footer>
                <?php include 'footer.php'?>
            </footer>
        </main>
    </body>
</html>
