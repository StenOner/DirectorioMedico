package com.example.alberto.directoriomedico.datos;

import android.provider.BaseColumns;

public class PacientesContract {
    public static abstract class PacientesEntry implements BaseColumns {
        public static final String TABLE_NAME = "pacientes";

        public static final String ID = "id";
        public static final String ID_MEDICO = "id_medico";
        public static final String NAME = "name";
        public static final String DNI = "dni";
        public static final String PHONE_NUMBER = "phoneNumber";
        public static final String AVATAR_URI = "avatarUri";
    }
}
