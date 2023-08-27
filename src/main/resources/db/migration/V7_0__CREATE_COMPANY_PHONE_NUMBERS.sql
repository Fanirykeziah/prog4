create table if not exists "company_phone_numbers"(
   id SERIAL PRIMARY KEY,
   company_id INTEGER NOT NULL,
   phone_numbers VARCHAR(13) NOT NULL,
   FOREIGN KEY (company_id) REFERENCES company_conf(id) ON DELETE CASCADE
 );
