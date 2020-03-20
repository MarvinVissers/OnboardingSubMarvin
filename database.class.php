<?php
    // Class aanmaken
    class Database {
        // Variabele aanmaken voor de connectie
        public $conn;

        // De gegevens om de connectie mee te maken
        private $host = 'localhost';
        private $db_name = 'onboarding';
        private $username = 'root';
        private $password = '';

        // Connectie maken
        public function getCOnnection() {
            $this->conn = null;

            // Kijken of de connectie gemaakt kan worden en anders een foutmelding geven
            try {
                $this->conn = new PDO('mysql:host=' . $this->host . ';dbname=' . $this->db_name, $this->username, $this->password);
            } catch (\PDOException $exception) {
                echo 'Connection error: ' . $exception->getMessage();
            }

            // Connectie teruggeven
            return $this->conn;
        }
    }
?>