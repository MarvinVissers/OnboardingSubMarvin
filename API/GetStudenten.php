<?php
    /**
    * Voorbeeld van data ophalen en json teruggeven
    */

    // Database connection class aanspreken
    require_once('database.class.php');

    // Variabele aanmaken voor connectie
    $database = new Database();
    $db = $database->getConnection();

    // Query maken
    $query = sprintf('SELECT * FROM student');
    // Query preparen, aka kijken of die uitgevoerd kan worden
    $stm = $db->prepare($query);
    // Als de query uitgevoerd wordt
    if($stm->execute()) {
        // Resultaat ophalen
        $result = $stm->fetchAll(PDO::FETCH_OBJ);
        
        // JSON teruggeven
        echo json_encode(array("item"=>$result));
    }
?>
