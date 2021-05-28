package com.example.mynotes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SearchEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

import java.util.List;

import javax.xml.parsers.FactoryConfigurationError;

//Если мы работаем с фрагментами через
//add, то надо удалять фрагменты до добавления, при replace в удалении нет необходимости, а если
//мы делаем возврат через back stack, то должны и по кнопке использовать back stack.
//когда возвращаемся назад, хорошо просто делать поп бэк стек
//если хотим, чтобы, когда возвращаемся назад, нам показывали обновленные данные настроек, надо писать обработчик в он резьюм

//меню! добавляется в ресурсах

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        readSettings(); //читаем настройки - вдруг с прошлого запуска что изменилось
        initView();
    }

    private void initView() {
        //хотим по кнопочкам открывать нужные фрагменты
        initButtonMain();
        initButtonFavorite();
        initButtonSettings();
        initButtonBack();
        initToolBar();
        Toolbar toolbar = initToolBar(); //тулбар нужен, чтобы, когда мы жмем на кнопочку, открывалась шторка
        initDrawer(toolbar); //шторка меню
    }

    private void initDrawer(Toolbar toolbar) {
        //инициализируем дровер дайаут. Идем в макет и вместо координат лайаут делаем дроуэр лайаут и добавляем пару строк
        final DrawerLayout drawer = findViewById(R.id.drawer_layout);
        //когда шторка отодвигается, у тулбара двигается стрелочка. Меняется анимация. Ниже синхронизируем это все.
        //тут гамбургер превращается в стрелочку
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);

        //можем как с попапом
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                if (navigateFragment(id)){
                    drawer.closeDrawer(GravityCompat.START);
                    return true;
                }
                return false;
            }
        });
    }

    private boolean navigateFragment(int id) {
        switch (id) {
            case R.id.action_settings:
                addFragment(new SettingsFragment());
                return true;
            case R.id.action_main:
                addFragment(new MainFragment());
                return true;
            case R.id.action_favorite:
                addFragment(new FavoriteFragment());
                return true;
        }
        return false;
    }

    private Toolbar initToolBar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar); //эта строчка нужна всем тулбарам
        return toolbar; //это добавляем для шторки, дотого метод был войд
    }

    //назначим экшн-бару меню
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        // Обработка выбора пункта меню приложения (активити)
        //перехватываем меню, когда его кликают, находим, что это кнопка, которая нам нужна, и обавляем фрагмент.
        //Это те же кнопки, что мы делали, но теперь через меню. Но надо еще создать меню! переопределяем он креейт опшн меню
        int id = item.getItemId();
        switch(id){
            case R.id.action_settings:
                addFragment(new SettingsFragment());
                return true;
            case R.id.action_main:
                addFragment(new MainFragment());
                return true;
            case R.id.action_favorite:
                addFragment(new FavoriteFragment());
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        MenuItem search = menu.findItem(R.id.action_search); //файнд айди для меню
        SearchView searchText = (SearchView) search.getActionView(); //это строка поиска
        searchText.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                //выведем на экран, что будет напечатано
                Toast.makeText(MainActivity.this, query, Toast.LENGTH_SHORT).show();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return true;
            }
        });


        return super.onCreateOptionsMenu(menu);
    }

    private void initButtonFavorite() {
        Button buttonBack = findViewById(R.id.buttonFavorite);
        buttonBack.setOnClickListener(v -> addFragment(new FavoriteFragment()));
    }

    private void initButtonSettings() {
        Button buttonBack = findViewById(R.id.buttonSettings);
        buttonBack.setOnClickListener(v -> addFragment(new SettingsFragment()));
    }

    private void initButtonMain() {
        Button buttonMain = findViewById(R.id.buttonMain);
        buttonMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addFragment(new MainFragment());
            }
        });
    }

    //добавляем заданный фрагмент во фрагмент менеджер
    private void addFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        if(Settings.isDeleteBeforeAdd)
        {
            Fragment fragmentToRemove = getVisibleFragment(fragmentManager);
            if (fragmentToRemove != null){
                transaction.remove(fragmentToRemove);
            }
        }

        if(Settings.isAddFragment) {
            transaction.add(R.id.fragment_container, fragment);
        }else{ //это если реплейс
            transaction.replace(R.id.fragment_container, fragment);
        }

        if(Settings.isBackStack){
            transaction.addToBackStack(null);
        }

        transaction.commit();

    }

    //елис настройка реплейс, контейнер будет вытеснять фрагмент, и будет пустое окно
    //а если эдд? Ой! Все друг на друга наложились, т.к. фрагменты прозрачные. Иногда такое нужно, но часто нет. Если нет, пользуем реплейс.
    private void initButtonBack() {
        Button buttonBack = findViewById(R.id.buttonBack);
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getSupportFragmentManager();
                if (Settings.isBackAsRemove){
                    //ели нажато, то ищем фрагмент, который отображается на экране
                    Fragment fragment = getVisibleFragment(fragmentManager);
                    //если нашли видимый фрагмент
                    if (fragment != null){
                        fragmentManager.beginTransaction().remove(fragment).commit(); //удаляем
                    } else{
                        fragmentManager.popBackStack(); // тогда просто вытолкнем что есть (или нет)
                    }
                }
            }
        });
    }

    //пробегаемся по стеку фрагментов и ищем тот, что находится сверху. Он и видимый же!
    private Fragment getVisibleFragment(FragmentManager fragmentManager) {
        List<Fragment> fragments = fragmentManager.getFragments(); //такой метод, возвращает все фрагменты
        int countFragment = fragments.size();
        for (int i = countFragment-1; i >=0; i--) { //идем от последнего, пока не равно нулю
            //порядок возвращаемых фрагментов может быть сбит, но СКОРЕЕ ВСЕГО последний - видимый, потому начинаем с конца.
            Fragment fragment = fragments.get(i);
            if (fragment.isVisible()){
                return fragment;
            }
        }
        return null;
    }

    private void readSettings() {
        SharedPreferences sharedPreferences = getSharedPreferences(Settings.SHARED_PREFERENCE_NAME, MODE_PRIVATE);
        //получаем нашу шарэд преференс
        Settings.isAddFragment = sharedPreferences.getBoolean(Settings.IS_ADD_FRAGMENT_USED, false); //это если значение не записано, какую булеан берем по умолчанию
                //получать данные транзакцией, увы, не можем. Либо персонально, либо скопом.
        Settings.isBackStack = sharedPreferences.getBoolean(Settings.IS_BACK_STACK_USED, true); //тру просто для разнообразия
        Settings.isBackAsRemove = sharedPreferences.getBoolean(Settings.IS_BACK_AS_REMOVE_FRAGMENT, true);
        Settings.isDeleteBeforeAdd = sharedPreferences.getBoolean(Settings.IS_DELETE_FRAGMENT_BEFORE_ADD, false);
    }
}