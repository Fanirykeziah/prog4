create table if not exists "employe_phone_numbers"(
   id SERIAL PRIMARY KEY,
   employe_id INTEGER NOT NULL,
   phone_numbers VARCHAR(13) NOT NULL,
   FOREIGN KEY (employe_id) REFERENCES employee(id) ON DELETE CASCADE
 );
