CREATE TABLE candidates (
    id INTEGER NOT NULL PRIMARY KEY AUTO_INCREMENT,
    heroName VARCHAR(64)  NOT NULL,
    heroClass VARCHAR(64)  NOT NULL,
    level INTEGER  NOT NULL,
    experience INTEGER  NOT NULL,
    attack INTEGER  NOT NULL,
    defense INTEGER  NOT NULL,
    hitPoints INTEGER  NOT NULL,
    weapon INTEGER  NULL,
    armor INTEGER  NULL,
    helm INTEGER  NULL
);
