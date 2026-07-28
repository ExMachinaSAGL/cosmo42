-- 1. Add the column allowing NULL temporarily
ALTER TABLE SPRING_AI_CHAT_MEMORY ADD COLUMN sequence_id BIGINT;

-- 2. Backfill existing rows using an INNER JOIN
UPDATE SPRING_AI_CHAT_MEMORY t
INNER JOIN (
    SELECT conversation_id, ROW_NUMBER() OVER (PARTITION BY conversation_id ORDER BY timestamp) - 1 AS seq
    FROM SPRING_AI_CHAT_MEMORY
) o ON t.conversation_id = o.conversation_id
SET t.sequence_id = o.seq;

-- 3. Enforce the NOT NULL constraint
ALTER TABLE SPRING_AI_CHAT_MEMORY MODIFY COLUMN sequence_id BIGINT NOT NULL;

-- 4. Create the required performance index
CREATE INDEX SPRING_AI_CHAT_MEMORY_CONVERSATION_ID_SEQUENCE_ID_IDX
ON SPRING_AI_CHAT_MEMORY(conversation_id, sequence_id);