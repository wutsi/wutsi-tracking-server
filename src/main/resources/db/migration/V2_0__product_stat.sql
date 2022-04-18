CREATE TABLE T_PRODUCT_STAT(
    id              SERIAL NOT NULL,

    period_fk       BIGINT NOT NULL REFERENCES T_PERIOD(id),
    merchant_id     BIGINT NOT NULL,
    product_id      BIGINT NOT NULL,
    view_count      BIGINT NOT NULL DEFAULT 0,
    share_count     BIGINT NOT NULL DEFAULT 0,
    chat_count      BIGINT NOT NULL DEFAULT 0,

    UNIQUE(period_fk, product_id),
    PRIMARY KEY (id)
);

CREATE INDEX I_PRODUCT_STAT_merchant_id ON T_PRODUCT_STAT (merchant_id);
CREATE INDEX I_PRODUCT_STAT_product_id ON T_PRODUCT_STAT (product_id);