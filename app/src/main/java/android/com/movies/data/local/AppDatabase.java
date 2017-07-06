/*
 * Copyright 2017, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package android.com.movies.data.local;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;
import android.com.movies.data.local.dao.MovieDao;
import android.com.movies.data.local.entity.MovieEntity;

@Database(entities = {MovieEntity.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    static final String DATABASE_NAME = "popular-movies-db";

    public abstract MovieDao movieDao();
}
