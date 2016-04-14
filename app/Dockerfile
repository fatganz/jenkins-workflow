FROM php:5.6-cli
COPY . /opt/app
WORKDIR /opt/app/web
RUN curl -sS https://getcomposer.org/installer | php
RUN php composer.phar install
CMD [ "php", "-S", "0.0.0.0:8080", "./index.php" ]
