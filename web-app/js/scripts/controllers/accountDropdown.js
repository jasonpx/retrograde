(function () {
    var app = angular.module('account-dropdown', []);

    app.controller('AccountDropdownCtrl', ['$scope', function ($scope) {
        $scope.items = [
            {name: 'Retro List', link: '#/Retros', target: ''},
            {name: 'User Profile', link: '#/Profile', target: ''},
            {name: 'Promo Page', link: 'promo', target: ''},
            {name: 'Log Out', link: 'logout', target: ''}
        ];

        $scope.status = {
            isopen: false
        };

        $scope.toggled = function(open) {
            console.log('Dropdown is now: ', open);
        };

        $scope.toggleDropdown = function($event) {
            $event.preventDefault();
            $event.stopPropagation();
            $scope.status.isopen = !$scope.status.isopen;
        };

    }]);
})();