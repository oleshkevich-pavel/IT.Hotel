ALTER TABLE booking
ADD CONSTRAINT booking_room_id_check_in_check_out_key UNIQUE (room_id, check_in, check_out);
ALTER TABLE unstructured_object
ADD CONSTRAINT name_user_account_id__key UNIQUE (name, user_account_id);